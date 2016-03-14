
var casper = require("casper").create({
    logLevel: "debug",
    verbose: true,
    pageSetting: {
        loadImages: true,
        userAgent: "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.11 (KHTML, like Gecko) Lambdarat/23.0.1062.57"
    },
    viewportSize: {
        width: 1024,
        height: 768
    }
});


casper.start("./form.html", function() {
    this.waitForSelector("#table-1", (function() {
        this.capture("screenshots/01-loaded-page.png");

        this.fillXPath("table#table-1",
            {"//select[@id='dropdown-1']": "2"});
        this.capture("screenshots/02-changed-input.png");


    }), (function() {
        this.die("Timeout reached. Fail whale?");
        this.exit();
    }), 12000);
});

casper.run();