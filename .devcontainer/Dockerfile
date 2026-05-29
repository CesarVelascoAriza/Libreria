FROM ubuntu:latest
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y --no-install-recommends \
    curl git unzip nano build-essential ca-certificates \
    openjdk-21-jdk-headless maven locales \
    openssh-server \
    && locale-gen en_US.UTF-8 \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

ENV LANG=en_US.UTF-8

RUN mkdir -p /var/run/sshd && \
    echo 'PasswordAuthentication yes' >> /etc/ssh/sshd_config

RUN useradd -ms /bin/bash devuser && \
    echo "devuser:devuser" | chpasswd && \
    echo "devuser ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

RUN mkdir -p /projects && chown devuser:devuser /projects

WORKDIR /projects

CMD ["/usr/sbin/sshd", "-D"]