(require '[clojure.core.async :refer [chan <! >!]])

(def channels (atom {}))

(defn register-channel
  [event-type ch]
  (swap! channels assoc event-type ch)
  ch)

(defn unregister-channel
  [event-type ch]
  (swap! channels dissoc event-type ch)
  ch)

(defn publish
  [event-type event]
  (let [channels-for-event (get @channels event-type)]
    (when channels-for-event
      (doseq [ch channels-for-event]
        (>! ch event)))))

(def click-channel (register-channel :click (chan)))

(publish :click {:x 10 :y 20})

(<! click-channel)

(publish :click {:x 30 :y 40})

(<! click-channel)
