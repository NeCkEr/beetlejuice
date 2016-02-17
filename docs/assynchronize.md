```clojure
(defn print-file [f1]
  (asynchronize
    (def f1-content (.readFile fs f1 "utf8" ...))
    (console/log f1-content)))
```

```js
function print-file(f1){
  fs.readFile(f1, 'utf8', function(f1-content){
    console.log(f1-content)
  });
}
```
