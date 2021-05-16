(ns stackcalc.ops-test (:require [clojure.test :as t]
                                 [stackcalc.ops :as ops]))

(t/deftest test-peek
  (t/testing "error if empty"
    (t/is (= (ops/stack-peek '()) {:error "Attempt to peek at empty stack"})))
  (t/testing "last value if not empty"
    (t/is (= (ops/stack-peek '(1 2 3))
             {:new-stack '(1 2 3)
              :response 1}))))

(t/deftest test-push
  (t/testing
   "works for empty"
    (t/is (= (ops/stack-push '() 37)
             {:new-stack '(37)
              :response 37})))
  (t/testing
   "works for non-empty"
    (t/is (= (ops/stack-push '(1 2 3) 37)
             {:new-stack '(37 1 2 3)
              :response 37}))))

(t/deftest test-pop
  (t/testing
   "error if empty"
    (t/is (= (ops/stack-pop '())
             {:error "Attempt to pop empty stack"})))
  (t/testing
   "works if non-empty"
    (t/is (= (ops/stack-pop '(1 2 3))
             {:new-stack '(2 3)
              :response 1}))))

(t/deftest test-add
  (t/testing
   "error if empty"
    (t/is (= (ops/stack-add '())
             {:error "Attempt to add stack with too few elements"})))
  (t/testing
   "error if only one element"
    (t/is (= (ops/stack-add '(1))
             {:error "Attempt to add stack with too few elements"})))
  (t/testing
   "add if two elements"
    (t/is (= (ops/stack-add '(1 2))
             {:new-stack '(3)
              :response 3})))
  (t/testing
   "add if more than two elements"
    (t/is (= (ops/stack-add '(1 2 3))
             {:new-stack '(3 3)
              :response 3}))))

(t/deftest test-subtract
  (t/testing
   "error if empty"
    (t/is (= (ops/stack-subtract '())
             {:error "Attempt to subtract stack with too few elements"})))
  (t/testing
   "error if only one element"
    (t/is (= (ops/stack-subtract '(1))
             {:error "Attempt to subtract stack with too few elements"})))
  (t/testing
   "subtract if two elements"
    (t/is (= (ops/stack-subtract '(1 2))
             {:new-stack [1]
              :response 1})))
  (t/testing
   "subtract if more than two elements"
    (t/is (= (ops/stack-subtract '(1 2 3))
             {:new-stack '(1 3)
              :response 1}))))

(t/deftest test-multiply
  (t/testing
   "error if empty"
    (t/is (= (ops/stack-multiply '())
             {:error "Attempt to multiply stack with too few elements"})))
  (t/testing
   "error if only one element"
    (t/is (= (ops/stack-multiply '(1))
             {:error "Attempt to multiply stack with too few elements"})))
  (t/testing
   "multiply if two elements"
    (t/is (= (ops/stack-multiply '(2 3))
             {:new-stack [6]
              :response 6})))
  (t/testing
   "multiply if more than two elements"
    (t/is (= (ops/stack-multiply '(2 3 4))
             {:new-stack '(6 4)
              :response 6}))))

(t/deftest test-divide
  (t/testing
   "error if empty"
    (t/is (= (ops/stack-divide '())
             {:error "Attempt to divide stack with too few elements"})))
  (t/testing
   "error if only one element"
    (t/is (= (ops/stack-divide '(1))
             {:error "Attempt to divide stack with too few elements"})))
  (t/testing
   "divide if two elements"
    (t/is (= (ops/stack-divide '(2 3))
             {:new-stack [3/2]
              :response 3/2})))
  (t/testing
   "divide if more than two elements"
    (t/is (= (ops/stack-divide '(2 3 4))
             {:new-stack '(3/2 4)
              :response 3/2}))))