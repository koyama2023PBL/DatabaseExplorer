FROM ubuntu:latest

RUN apt-get update
RUN apt-get install -y openssh-server sysstat util-linux coreutils
RUN useradd -m explorer && echo "explorer:explorer" | chpasswd
RUN mkdir /var/run/sshd

EXPOSE 2222

CMD ["/usr/sbin/sshd", "-D", "-p", "2222"]
