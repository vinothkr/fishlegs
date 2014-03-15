(ns fishlegs.query
  (require [monger.collection :as mongo]
           [monger.operators :refer :all]))

(def oplookup 
  {"<" $lt
   ">" $gt
   "<=" $lte
   ">=" $gte})


(defn metrics [& clauses]
  (mongo/find-maps "metric" (first (reduce conj clauses {}))))

(defmacro having [field & opvalues]
  {field (reduce conj (map (fn[x] {(oplookup (str (first x))) (second x)}) opvalues))})
  
