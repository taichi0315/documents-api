FROM hseeberger/scala-sbt:8u212_1.2.8_2.12.8

WORKDIR /workspace

ADD build.sbt /workspace/build.sbt
ADD app /workspace/app
ADD conf /workspace/conf
ADD project/plugins.sbt /workspace/project/plugins.sbt
ADD project/build.properties /workspace/project/build.properties
