# sling-camel-playground
Experimenting with Apache Camel in Apache Sling

To run this, build the whole thing with `mvn clean install` and run the executable jar found under `launchpad/target`.

That launchpad includes the standard OSGi webconsole at http://localhost:8080/system/console/ and the Composum browser at http://localhost:8080/bin/browser.html

## Test URLs
For now, the following test sequence calls read/write Apache Camel pipelines, using the standard Sling
request routing mechanisms:

    curl -u admin:admin -X POST "http://localhost:8080/camel/chat.provider/some-channel?name=me&message=hello+at+$(date +%s)"
	curl http://localhost:8080/camel/file.provider/some-channel.txt
	curl http://localhost:8080/camel/file.provider/some-channel.txt:uppercase

