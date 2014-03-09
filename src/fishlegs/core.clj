(ns fishlegs.core
  (:require [monger.core :as mongo]))

(defn connect [host]
  (mongo/connect-via-uri! (format "mongodb://%s/metrics" host)))
