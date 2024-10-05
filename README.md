Use Maven (and the Dockerfile) to build this project. The Dockerfile is located in the root directory of the project. To build the project, you can run the following command:

docker build . -t solu-print-center:latest

docker run -d -p 8080:8080 solu-print-center:latest

Here are the sample cURL requests for the 2 API endpoints:

curl --request POST \
--url http://localhost:8080/printCenter/schedulePrint \
--header 'Content-Type: multipart/form-data' \
--form 'files=./Enterprise Car Receipt.pdf' \
--form 'files=docker run -d -p 8080:8080 solu-print-center:latest/Online Return Center.pdf' \
--form color=true \
--form 'notes=Some notes' \
--form 'requestedBy=Robert Carrier' \
--form meetingOwner=Solu \
--form meetingDate=2024-10-06

curl --request GET \
--url http://localhost:8080/printCenter/getPrintRequests

