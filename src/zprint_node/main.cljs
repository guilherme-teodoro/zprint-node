(ns zprint-node.main
  (:require
    ["commander" :as program]
    ["fs" :as fs]
    ["path" :as path]
    [cljs.reader :as reader]
    [zprint.core :as zprint]))

(def cwd
  (js/process.cwd))

(defn get-options
  [config-path]
  (let [project-path (path/resolve cwd ".zprintrc")]
    (cond (fs/existsSync config-path)  (-> config-path
                                           (fs/readFileSync "utf8")
                                           (reader/read-string))
          (fs/existsSync project-path) (-> cwd
                                           (path/resolve ".zprintrc")
                                           (fs/readFileSync "utf8")
                                           (reader/read-string))
          :else                        {})))

(defn main
  [_]
  (doto program
    (.version "0.1.0")
    (.option "-i, --input <path>" "input file (.cljs, .clj, .cljc)")
    (.option "-o, --output <path>" "output file (.cljs, .clj, .cljc)")
    (.option "-c, --config <path>" "config file (edn file)")
    (.parse (.-argv js/process)))
  (when-let [input (.-input program)]
    (let [file-path     (path/resolve cwd (.-input program))
          src           (fs/readFileSync file-path "utf8")
          zprinted-file (zprint/zprint-file-str src file-path (get-options (.-config program)))]
      (if (.-output program)
        (fs/writeFileSync (.-output program) zprinted-file "utf8")
        (js/process.stdout.write zprinted-file)))
    (js/process.exit 0)))
