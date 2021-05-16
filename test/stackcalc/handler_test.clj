(ns stackcalc.handler-test
  (:require [clojure.test :as t]
            [ring.mock.request :as mock]
            [stackcalc.handler :refer [app]]))

(t/deftest test-app
  (t/testing "main route"
    (let [response (app (mock/request :get "/calc/1/push/10"))]
      (t/is (= (:status response) 200))
      (t/is (= (:body response) "10"))))

  (t/testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (t/is (= (:status response) 404)))))
