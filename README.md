# stackcalc

Simple stack-based calculator, allowing muliple concurrent stacks.

Supported operations (where the `:id` parameter is used to select a specific stack):

- `/calc/:id/peek` returns the top of the stack
- `/calc/:id/push/<n>` pushes the number `n` onto the stack and returns it
- `/calc/:id/pop` removes and returns the top of the stack
- `/calc/:id/add` `pops` twice, yielding values (`a`) and (`b`), then pushes the sum `b + a` onto the stack and returns it.
- `/calc/:id/subtract` `pops` twice, yielding values (`a`) and (`b`), then pushes the difference `b - a` onto the stack and returns it.
- `/calc/:id/multiply` `pops` twice, yielding values (`a`) and (`b`), then pushes the multiplication `b * a` onto the stack and returns it.
- `/calc/:id/divide` `pops` twice, yielding values (`a`) and (`b`), then pushes the multiplication `b * a` onto the stack and returns it.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

To run tests, run:

    lein test
