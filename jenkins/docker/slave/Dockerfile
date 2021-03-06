FROM openjdk:8-jdk

LABEL maintainer "yannick.lacaute@gmail.com"

# Install all necessary packages
RUN apt-get update -qq && apt-get install -qqy \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    software-properties-common \
    git \
    build-essential \
    openssh-server \
    cowsay \
    && rm -rf /var/lib/apt/lists/*

# SSH config - https://docs.docker.com/engine/examples/running_ssh_service/
RUN mkdir /var/run/sshd
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

# Add the jenkins user
ARG uid=1000
ARG gid=1000
RUN addgroup --gid ${gid} jenkins
RUN useradd jenkins --create-home --home /home/jenkins --shell /bin/sh --uid ${uid} --gid ${gid}

# Only the ssh port is exposed
EXPOSE 22

# Start sshd
CMD ["/usr/sbin/sshd", "-D"]
