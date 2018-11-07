(ns zprint-node.main
  (:require ["commander" :as program]
            ["fs" :as fs]
            ["path" :as path]
            [cljs.reader :as reader]
            [zprint.core :as zprint]))

(defn get-options
  [cwd]
  (let [path (path/resolve cwd ".zprintrc")]
    (if (fs/existsSync path)
      (-> cwd
          (path/resolve ".zprintrc")
          (fs/readFileSync "utf8")
          (reader/read-string))
      {})))

(defn main
  [_]
  (doto program
    (.version "0.1.0")
    (.option "-i, --input <path>" "input file")
    (.option "-o, --output <path>" "output file")
    (.parse (.-argv js/process)))
  (when-let [input (.-input program)]
    (let [cwd (js/process.cwd)
          file-path (path/resolve cwd (.-input program))
          src (fs/readFileSync file-path "utf8")
          zprinted-file
            (zprint/zprint-file-str src file-path (get-options cwd))]
      (if (.-output program)
        (fs/writeFileSync (.-output program) zprinted-file "utf8")
        (js/process.stdout.write zprinted-file)))
    (js/process.exit 0)))
