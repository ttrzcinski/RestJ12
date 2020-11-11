#!/bin/bash
printf "\nWill now test, if 'add new message' works.\n"
# Adding new message
curl -X POST http://127.0.0.1:8080/api/message -H "Content-type:application/json" \
 -d '{"email": "test@example.com", "title": "Test title 1", "content": "Lorem ipsum", "magicnumber": 6}'
# Reading messages
printf "\n\nWill now test, if 'read some messages' works.\n"
curl -X GET http://127.0.0.1:8080/api/messages/test@example.com -H "Content-type:application/json"
# Sending messages
printf "\n\nWill now test, if 'sending' works\n"
curl -X POST http://127.0.0.1:8080/api/send -H "Content-type:application/json"
# Reading messages again
printf "\n\nWill now test, if 'read some messages' will return something.\n"
curl -X GET http://127.0.0.1:8080/api/messages/test@example.com -H "Content-type:application/json"
printf "\n\nDONE\n"
