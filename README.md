## MicroLambda v 0.2

Project is a AWS Lambda written in Java 11 (actually runs on 12-th) with use of the newest Micronaut 1.1.0.

From DevOps perspective it is interesting, because it uses MVN, Gradle and Docker together, so in the end AWS Lambda runs locally in container without AWS Cloud.

# To build it:
sh just_build_it.sh

# To test it:
sh test_it.sh 

## IT WOULDN'T BE DONE WITHOUT

[Docker](docker.com)

[AWS Lambda Handler](https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html)

[Handler JavaDoc](https://micronaut-projects.github.io/micronaut-aws/latest/api/io/micronaut/function/aws/proxy/MicronautLambdaHandler.html)

[Micronaut AWS Lambda Function documentation](https://micronaut-projects.github.io/micronaut-aws/latest/guide/index.html#lambda)

## Authors
Project Starter: [Tomasz Trzciński](mailto:trzcinski.tomasz.1988@gmail.com)
Security: [Snyk](https://github.com/snyk-bot)
