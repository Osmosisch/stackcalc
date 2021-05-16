(ns stackcalc.ops)


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

(defn stack-add [stack]
  (if (< (count stack) 2)
    {:error "Attempt to add stack with too few elements"}
    (let [val1 (peek stack)
          val2 (peek (pop stack))
          popped-stack (pop (pop stack))
          sum (+ val1 val2)]
      {:new-stack (conj popped-stack sum)
       :response sum})))