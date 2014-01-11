# email-address

Verify email addresses in JavaScript.

## TODO

-  IPv4/IPv6 IP address validation in the domain-part.
-  Limit of 64 chars for the local-part.
-  Limit of 255 chars for the domain-part.
-  Limit of 254 chars for entire email address.

## How to run tests

-   Clone the repo.
-   Make sure the Node Package Manager (npm) is installed.
    -   Use Homebrew on Mac, Linux package manager otherwise.
    -   Don't sudo install packages! Put `~/npm/bin` into `$PATH` then
        run `npm config set prefix ~/npm`.
-   Make sure bower is installed.

        npm install -g bower

-   Make sure grunt-cli is installed.

        npm install -g grunt-cli

-   Install dependencies:

        npm install
        bower install

-   Run tests

        grunt
