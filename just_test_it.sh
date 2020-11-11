#!/bin/bash
echo "Will now test, if 'add new message' works."
# Adding new message
curl -X POST http://127.0.0.1:8080/api/message -H "Content-type:application/json" \
 -d '{"email": "test@example.com", "title": "Test title 1", "content": "Lorem ipsum", "magicnumber": 6}'
# Reading messages
echo "Will now test, if 'read some messages' works."
curl -X GET http://127.0.0.1:5000/api/messages/test@example.com -H "Content-type:application/json"
# Sending messages
echo "Will now test, if 'sending' works"
curl -X POST http://127.0.0.1:5000/api/send -H "Content-type:application/json"
# Reading messages again
echo "Will now test, if 'read some messages' will return something."
curl -X GET http://127.0.0.1:5000/api/messages/test@example.com -H "Content-type:application/json"
echo "DONE"