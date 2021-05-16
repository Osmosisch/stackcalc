(ns stackcalc.ops)

;; implement stack operations
(defn stack-peek [stack]
  (if (empty? stack)
    {:error "Attempt to peek at empty stack"}
    {:new-stack stack
     :response (peek stack)}))

(defn stack-push [stack val]
  {:new-stack (conj stack val)
   :response val})

(defn stack-pop [stack]
  (if (empty? stack)
    {:error "Attempt to pop empty stack"}
    (let [val (peek stack)]
      {:new-stack (pop stack)
       :response val})))

(defn- binary-op
  "Apply op to the two top elements of stack, i.e. (op top-1 top), 
   with top-1 being the element 1 below top. Assumes stack size >= 2"
  [stack op]
  (let [[val1 val2 & rest] stack
        result (op val2 val1)]
    {:new-stack (conj rest result)
     :response result}))

(defn stack-add [stack]
  (if (< (count stack) 2)
    {:error "Attempt to add stack with too few elements"}
    (binary-op stack +)))

(defn stack-subtract [stack]
  (if (< (count stack) 2)
    {:error "Attempt to subtract stack with too few elements"}
    (binary-op stack -)))

(defn stack-multiply [stack]
  (if (< (count stack) 2)
    {:error "Attempt to multiply stack with too few elements"}
    (binary-op stack *)))

(defn stack-divide [stack]
  (if (< (count stack) 2)
    {:error "Attempt to divide stack with too few elements"}
    (binary-op stack /)))