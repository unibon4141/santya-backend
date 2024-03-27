#======================================================================================================================
# sbt
#======================================================================================================================
FROM --platform=linux/x86_64 adoptopenjdk:11.0.11_9-jdk-hotspot as sbt

USER root

SHELL ["/bin/bash", "-o", "pipefail", "-c"]

RUN apt-get update && apt-get install --no-install-recommends -y \
    apt-transport-https \
    ca-certificates \
    gnupg \
 && echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list \
 && echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list \
 && curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add \
# && curl -sL "https://deb.nodesource.com/setup_12.x" | bash - \
 && apt-get update && apt-get install --no-install-recommends -y \
    sbt=1.9.6 \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

WORKDIR /tmp
ENV SBT_OPTS -Dsbt.ivy=true -Dsbt.ivy.home=/root/.ivy2
RUN sbt sbtVersion \
  && mkdir -p project

COPY ./build.sbt .
COPY ./project/build.properties ./project
COPY ./project/plugins.sbt ./project

RUN echo "case object Tmp" > Tmp.scala \
 && sbt compile

#======================================================================================================================
# build
#======================================================================================================================
FROM --platform=linux/x86_64 adoptopenjdk:11.0.11_9-jdk-hotspot as build

USER root

SHELL ["/bin/bash", "-o", "pipefail", "-c"]

RUN apt-get update && apt-get install --no-install-recommends -y \
    apt-transport-https \
    ca-certificates \
    gnupg \
 && echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list \
 && echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list \
 && curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add \
# && curl -sL "https://deb.nodesource.com/setup_12.x" | bash - \
 && apt-get update && apt-get install --no-install-recommends -y \
    sbt=1.9.6 \
#    nodejs \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

COPY --from=sbt /root/.ivy2 /root/.ivy2
COPY --from=sbt /root/.sbt /root/.sbt
COPY . /santya-backend
EXPOSE 9000
WORKDIR /santya-backend
ENV SBT_OPTS -Xmx3G -Xss4M -XX:+CMSClassUnloadingEnabled -Dsbt.ivy=true -Dsbt.ivy.home=/root/.ivy2
RUN sbt "clean; compile; dist"

#======================================================================================================================
# runtime
#======================================================================================================================
FROM --platform=linux/x86_64 adoptopenjdk:11.0.11_9-jdk-hotspot

USER root

ENV LANG C.UTF-8
ENV TZ Asia/Tokyo
ENV JAVA_OPTS -Dfile.encoding=UTF-8 --add-opens java.base/java.lang=ALL-UNNAMED

RUN apt-get update && apt-get install --no-install-recommends -y \
    unzip \
 && apt-get clean \
 && rm -rf /var/lib/apt/lists/*

COPY --from=build /santya-backend/target/universal/santya-backend-1.0-SNAPSHOT.zip /tmp
COPY --from=build /santya-backend/run.sh /tmp

RUN groupadd --gid 1001 appserver \
 && useradd --gid 1001 --uid 1001 -m appserver

USER appserver

RUN unzip /tmp/santya-backend-1.0-SNAPSHOT.zip -d /home/appserver/ \
 && cp -i /tmp/run.sh /home/appserver/ \
 && chmod 755 /home/appserver/run.sh \
 && chmod 755 /home/appserver/santya-backend-1.0-SNAPSHOT/bin/santya-backend \
 && chmod 777 /public/

WORKDIR /home/appserver

CMD ["bash", "./run.sh"]