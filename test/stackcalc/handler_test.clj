(ns stackcalc.handler-test
  (:require [clojure.test :as t]
            [ring.mock.request :as mock]
            [stackcalc.handler :refer [app]]))

(defn- expect [route expected-status expected-body]
  (let [response (app (mock/request :get route))]
    (t/is (= (:status response) expected-status))
    (t/is (= (:body response) expected-body))))

(t/deftest test-app
  (t/testing "main route"
    (expect "/calc/1/push/10" 200 "10"))
  (t/testing
   "exercise scenario"
    (expect "/calc/1/push/1" 200 "1")
    (expect "/calc/1/push/4" 200 "4")
    (expect "/calc/1/peek" 200 "4")
    (expect "/calc/1/add" 200 "5")
    (expect "/calc/1/push/10" 200 "10")
    (expect "/calc/1/multiply" 200 "50")
    (expect "/calc/1/push/2" 200 "2")
    (expect "/calc/1/divide" 200 "25")
    (expect "/calc/2/peek" 400 "Attempt to peek at empty stack")
    (expect "/calc/2/push/20" 200 "20")
    (expect "/calc/2/divide" 400 "Attempt to divide stack with too few elements"))
  (t/testing
   "divide by zero"
    (expect "/calc/1/push/1" 200 "1")
    (expect "/calc/1/push/0" 200 "0")
    (expect "/calc/1/divide" 400 "Divide by zero"))
  (t/testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (t/is (= (:status response) 404)))))



