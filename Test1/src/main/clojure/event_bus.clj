(require '[clojure.core.async :as async])

(defn event-bus []
  (async/chan))

(defn subscribe [event-bus event-name]
  (let [channel (async/chan)]
    (async/put! event-bus {:event-name event-name :channel channel})
    channel))

(defn publish [event-bus event-name data]
  (async/put! (:channel (async/<! event-bus)) {:event-name event-name :data
                                               data}))

(defn handler [event-bus event-name]
  (let [channel (subscribe event-bus event-name)]
    (while true
      (let [event (async/<! channel)]
        (println (str "Received event") (:event-name event) "with data" (:data event))))))

(defn main []
  (let [event-bus (event-bus)]
    (async/go-loop []
      (when-let [event (async/<! event-bus)]
        (case (:event-name event)
          "event1" (async/go (handler event-bus "event1"))
          "event2" (async/go (handler event-bus "event2"))
          "event3" (async/go (handler event-bus "event3")))))))
