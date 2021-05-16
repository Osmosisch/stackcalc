(ns stackcalc.handler
  (:require [clojure.edn :as edn]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [stackcalc.ops :as ops]))

(def stacks (atom {})) ; simplest possible store. next step probably to mock out promises to simulate 'real' store

(defn set-stack! [id new-stack]
  (swap! stacks #(assoc % id new-stack)))

(defn- get-stack
  "Get the stack with specified id; initialises an empty stack if none exists"
  [id]
  (if (@stacks id)
    (@stacks id)
    ((set-stack! id '()) ; list might be better than vector but i found vector easier to reason about
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
       :body (.toString response)})))

(defn- do-op!
  "Wrapper utility function to thread the operation through stack retrieval and error handling. Optional val parameter for the push case."
  ([id operation]
   (try (-> id
            (get-stack)
            (operation)
            (handle-ops-response! id))
        (catch Exception e
          {:status 400
           :body (.getMessage e)})))
  ([id operation val]
   (try (-> id
            (get-stack)
            (operation val)
            (handle-ops-response! id))
        (catch Exception e
          {:status 400
           :body (.getMessage e)}))))

(defroutes app-routes
  (GET "/calc/:id/peek" [id]
    (do-op! id ops/stack-peek))

  (GET "/calc/:id/push/:val" [id val]
    (do-op! id ops/stack-push (edn/read-string val)))

  (GET "/calc/:id/pop" [id]
    (do-op! id ops/stack-pop))

  (GET "/calc/:id/add" [id]
    (do-op! id ops/stack-add))

  (GET "/calc/:id/subtract" [id]
    (do-op! id ops/stack-subtract))

  (GET "/calc/:id/multiply" [id]
    (do-op! id ops/stack-multiply))

  (GET "/calc/:id/divide" [id]
    (do-op! id ops/stack-divide))

  (not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
