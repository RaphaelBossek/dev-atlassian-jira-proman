# What is this project about

One of the core activities as [Software Product Manager (SPM)](http://community.ispma.org/body-of-knowledge/) is to identify the most valuable ideas which drive your business so you can start your next product project based on them.

This task is done by [Software Product Manager (SPM)](http://community.ispma.org/body-of-knowledge/) in cooperation with other professionals, e.g. development, marketing or sales, who contribute with their domain specific knowledge.

One such tool is Demand Metric's [Business Strategy Prioritization Tool](http://www.demandmetric.com/content/business-strategy-prioritization-tool) which is done - of course, what else - in a spreadsheet.
This approach is suitable for a small number of simple topics. If the complexity increases, especially if you start describing your ideas in [Portfolio Epics](http://www.scaledagileframework.com/epic/), as Osterwalder's [Business Model Canvas](http://www.businessmodelgeneration.com/canvas/bmc) and [Value Proposition Design](https://strategyzer.com/books/value-proposition-design) advice, things become hard to manage.

This project is dedicated for all those [Software Product Managers (SPM)](http://community.ispma.org/body-of-knowledge/) who uses [Atlassian JIRA project management software](https://atlassian.com/software/jira) and want to make their decision making process involving and transparent to their organisation, especially for their [Product Owners](http://www.scaledagileframework.com/product-owner/).

With this plugin for [Atlassian JIRA project management software](https://atlassian.com/software/jira) you are able to:

* Define weightings which are used for polls
* Define polls with predefined weightings on a selected number of topics
* Invite a selected number of users to participate in a poll
* Analyse the poll results with descriptive statistics methods and visualise then in a [box plot](https://en.wikipedia.org/wiki/Box_plot)

# Status of the project

Because it's a spare time software development project for me, things going slowly but consistent. As soon as there is a working version you will find the corresponding plugin for download here including a comprehensive description here and on my [homepage](http://raphaelbossek.wordpress.com).
The final version will be made public in [Atlassian Marketplace](https://marketplace.atlassian.com/).

A small and short overview about the project plan:
- [x] Concept papers with maths, required software libraries and tools, database model, user interface finalised 
- [x] Descriptive statistics examples with box plot are working in [Mathematica](http://mathematica.stackexchange.com/questions/102911/inverted-empirical-cdf-with-averaging-including-boxwhiskerchart)
- [x] Development environment is working and version control system is up and running, including first sample code
- [ ] Definition of weighting is working
- [ ] Setup of polls is working
- [ ] Definition of weighting categories is working
- [ ] Execution of polls is working
- [ ] Analysing polls is working
- [ ] Finalise the documentation
- [ ] Start the field trial
- [ ] Finalise the automatic test cases with collected field trial data
- [ ] Publish the plugin to [Atlassian Marketplace](https://marketplace.atlassian.com/)

# Pre-requirements

This peace of software is developed for [Atlassian JIRA project management software](https://atlassian.com/software/jira) 6.4.12 and newer.

# License

```
The MIT License (MIT)

Copyright (c) 2016 Raphael Bossek

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

# Let's Start Coding

I'm developing on a Mac with [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/), [Atlassian JIRA SDK](https://developer.atlassian.com/docs/developer-tools/working-with-the-sdk) and [PostgreSQL](http://postgresapp.com/) as database backend.

Here are the SDK commands you'll use immediately:

* `atlas-run`   -- installs this plugin into the product and starts it on localhost
* `atlas-debug` -- same as atlas-run, but allows a debugger to attach at port 5005
* `atlas-cli`   -- after atlas-run or atlas-debug, opens a Maven command line window:
    * `pi` reinstalls the plugin into the running product instance
* `atlas-help`  -- prints description for all commands in the SDK

Full documentation is always available at:

https://developer.atlassian.com/display/DOCS/Introduction+to+the+Atlassian+Plugin+SDK