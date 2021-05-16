(ns stackcalc.ops-test (:require [clojure.test :as t]
                                 [stackcalc.ops :as ops]))

(t/deftest test-peek
  (t/testing "error if empty"
    (t/is (= (ops/stack-peek []) {:error "Attempt to peek at empty stack"})))
  (t/testing "last value if not empty"
    (t/is (= (ops/stack-peek [1 2 3])
             {:new-stack [1 2 3]
              :response 3}))))

(t/deftest test-push
  (t/testing
   "works for empty"
    (t/is (= (ops/stack-push [] 37)
             {:new-stack [37]
              :response 37})))
  (t/testing
   "works for non-empty"
    (t/is (= (ops/stack-push [1 2 3] 37)
             {:new-stack [1 2 3 37]
              :response 37}))))

(t/deftest test-pop
  (t/testing
   "error if empty"
    (t/is (= (ops/stack-pop [])
             {:error "Attempt to pop empty stack"})))
  (t/testing
   "works if non-empty"
    (t/is (= (ops/stack-pop [1 2 3])
             {:new-stack [1 2]
              :response 3}))))

(t/deftest test-add
  (t/testing
   "error if empty"
    (t/is (= (ops/stack-add [])
             {:error "Attempt to add stack with too few elements"})))
  (t/testing
   "error if only one element"
    (t/is (= (ops/stack-add [1])
             {:error "Attempt to add stack with too few elements"}))))