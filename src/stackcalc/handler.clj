(ns stackcalc.handler
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [stackcalc.ops :as ops]))

(def stacks (atom {})) ; simplest possible store. didn't have time to mock out promises but that

(defn set-stack! [id new-stack]
  (swap! stacks #(assoc % id new-stack)))

(defn- get-stack
  "Get the stack with specified id; initialises an empty stack if none exists"
  [id]
  (if (@stacks id)
    (@stacks id)
    ((set-stack! id [])
     id)))

(defn- handle-ops-response!
  "Return 200 and response body if the operation didn't return an error; otherwise return 400 and an error message body"
  [{:keys [error new-stack response]} id]
  (if error
    {:status 400
     :body error}
    (do
      (set-stack! id new-stack) ;not sure whether I've hit on the least 'side-effect' way to do this here
      {:status 200
       :body response})))

(defn- do-op!
  "Wrapper utility function to thread the operation through stack retrieval and error handling. Optional val parameter for the push case."
  ([id operation]
   (-> id
       (get-stack)
       (operation)
       (handle-ops-response! id)))
  ([id operation val]
   (-> id
       (get-stack)
       (operation val)
       (handle-ops-response! id))))

(defroutes app-routes
  (GET "/calc/:id/peek" [id]
    (do-op! id ops/stack-peek))

  (GET "/calc/:id/push/:val" [id val]
    (do-op! id ops/stack-push val))

  (GET "/calc/:id/pop" [id]
    (do-op! ops/stack-pop id))

  (GET "/calc/:id/add" [id]
    (do-op! ops/stack-add id))
  (not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
