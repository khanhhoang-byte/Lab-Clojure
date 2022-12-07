(ns test)
;; Напишите функцию, выбирающую из списка только элементы, встречающиеся более n-раз.
(defn find-duplicates [numbers]
  (->> numbers
       (frequencies)
       (filter (fn [[_ v]] (> v 2)))
       (keys)))

(find-duplicates [2 4 3 4 6 4 7 3 8 11 20 3 9])

