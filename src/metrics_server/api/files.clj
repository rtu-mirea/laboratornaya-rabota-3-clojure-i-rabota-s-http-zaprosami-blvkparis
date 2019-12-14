(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))

;; 2.1
(defn task_21 [files]
  (filter (fn [a] (not (get a :directory)))files))

;; 2.2
(defn task_22 [files]
  (filter (fn [a] (not (get a :executable)))files))

;; 2.3
(defn task_23 [files]
  (map (fn [file]
         {
          :name (clojure.string/replace (get file :name) #".conf" ".cfg")
          :size (get file :size)
          :changed (get file :changed)
          :directory (get file :directory)
          :executable (get file :executable)
          }
         ) files)
  )

;; 3
(defn task_3 [files] (/
                           (reduce + (map (fn [file] (get file :size))
                                          (filter (fn [a] (not (get a :directory))) files)))
                           (count (filter (fn [a] (not (get a :directory))) files))
                           ))

;; Вывод
(defn -main [& args]
  (println (task_21 (get-files)))
  (println (task_22 (get-files)))
  (println (task_23 (get-files)))
  (println (task_3 (get-files)))
  )

