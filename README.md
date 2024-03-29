[![Clojars Project](https://img.shields.io/clojars/v/beetlejuice.svg)](https://clojars.org/beetlejuice) [![Build Status](https://travis-ci.org/cncommerce/beetlejuice.svg?branch=master)](https://travis-ci.org/cncommerce/beetlejuice)

# beetlejuice

ClojureScript bindings for CasperJS.  With added ectoplasm.

# Prerequisities

## Install nvm & nodejs & npm & phantomJS & casperJS

    curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.26.1/install.sh | bash
    source ~/.nvm/nvm.sh
    nvm install v4.2.3
    nvm alias default v4.2.3
    npm install --global phantomjs@1.9.8
    npm install --global casperjs@1.1.0-beta3

# Compilation

Leave one terminal open and run this command:
```
$ lein with-profiles -dev,+test cljsbuild auto
```

To run the actual tests run
```
$ lein with-profiles -dev,+test test
```



## License

The MIT License (MIT)

Copyright (c) 2016 Condé Nast Commerce Ltd.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
documentation files (the "Software"), to deal in the Software without restriction, including without limitation the 
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit 
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the 
Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
