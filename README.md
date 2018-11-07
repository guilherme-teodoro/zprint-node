# zprint-node

Another node.js wrapper for [zprint](https://github.com/kkinnear/zprint)

## Installation

```sh
npm install -g zprint-node
```

## How to use

```sh
Usage: zprint-node [options]

Options:
  -V, --version        output the version number
  -i, --input <path>   input file (.cljs, .clj, .cljc)
  -o, --output <path>  output file (.cljs, .clj, .cljc)
  -c, --config <path>  config file (edn file)
  -h, --help           output usage information
```

## Configuration Interface

To learn how to create a `.zprintrc` see the [zprint documentation](https://github.com/kkinnear/zprint#introduction-to-configuration)

## Editor integration

### Emacs

```emacs-lisp
(defun zprint-this ()
  (interactive)
  (basic-save-buffer)
  (shell-command (concat "zprint-node -i " buffer-file-name " -o " buffer-file-name " -c " (projectile-project-root) ".zprintrc")))
```

## Thanks

- [zprint-cljs](https://github.com/roman01la/zprint-clj/) for the inspiration
- [zprint](https://github.com/kkinnear/zprint)

## License

MIT
