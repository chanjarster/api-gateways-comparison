FROM chanjarster/tomcat:8.5-alpine

ENV ENABLE_JMX_SSL=false

RUN cp -r /home/tomcat/tomcat/webapps-bak/* /home/tomcat/tomcat/webapps/

# disable examples/jsp/jsp2/simpletag/book.jsp session creation
WORKDIR /home/tomcat
RUN echo '<%@ page session="false"%>' > tmp.jsp
RUN cat /home/tomcat/tomcat/webapps-bak/examples/jsp/jsp2/simpletag/book.jsp >> tmp.jsp
RUN cp tmp.jsp /home/tomcat/tomcat/webapps/examples/jsp/jsp2/simpletag/book.jsp
RUN rm tmp.jsp