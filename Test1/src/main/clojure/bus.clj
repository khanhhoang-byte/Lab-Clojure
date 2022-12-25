(require '[clojure.core.async :as async])

(defonce channels (atom {}))

(defn subscribe
  [channel-name]
  (let [channel (async/chan)]
    (swap! channels assoc channel-name channel)
    channel))

(defn unsubscribe
  [channel-name]
  (swap! channels dissoc channel-name))

(defn publish
  [channel-name message]
  (let [channel (get @channels channel-name)]
    (when channel
      (async/put! channel message)
      true)))

(defn has-subscribers?
  [channel-name]
  (not (nil? (get @channels channel-name))))

