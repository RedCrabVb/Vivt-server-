========================================================================
# Сontract(API):
* Authorization
* Registration.
* Get a list of all messages.
* Get personalized data.
* Get a class schedule.
* Get academic achievement.
* Get news feed.
* Send message
* Push notifications
* "Elínos"
========================================================================

Everything works quite simply, the client sends a GET request,
and in response receives json or a 401 error, this is generally not very good



# /api/authorization
------------------------------------------------------
Inquiry: ?login=Mail&password=pass
Answer: {"token":"aaebccce-f501-4053-8d85-40f6dd158b46"}

The token will be needed in the 
future to identify the user
------------------------------------------------------





# /api/message
------------------------------------------------------
Inquiry: ?token=aaebccce-f501-4053-8d85-40f6dd158b46
Answer: {
           "msg":[
              {
                 "recipient":1,
                 "from_whom":2,
                 "header":"Main",
                 "text":"bal_bal_bla 12"
              },
              {
                 "recipient":1,
                 "from_whom":1,
                 "header":"sad",
                 "text":"sdaf"
              }
           ]
        }

Perhaps now I found a small error, instead 
of a username, its ID is sent
------------------------------------------------------





# /api/schedule
------------------------------------------------------
Inquiry: ?token=aaebccce-f501-4053-8d85-40f6dd158b46
Answer: {
             "schedule":[
                {
                   "day_of_week":"Mon",
                   "thingsArrayList":[
                      {
                         "number_pairs":1,
                         "audience":"",
                         "type":"",
                         "things":"",
                         "teacher":"  "
                      }
                      ...
                      {
                         "number_pairs":7,
                         "audience":"",
                         "type":"",
                         "things":"",
                         "teacher":"  "
                      }
                   ]
                },
                {
                   "day_of_week":"Tue",
                   "thingsArrayList":[
                      {
                         "number_pairs":1,
                         "audience":"",
                         "type":"",
                         "things":"",
                         "teacher":"  "
                      },
                      ...
                }
                ...
             }

The answer to the given request can be quite large, it is also worth noting
that for ease of development, some points were missed, for example: it is possible
that a pair is divided into subgroups, and then also on parity, oddness of the day of the week
------------------------------------------------------





# /api/news
------------------------------------------------------
Inquiry: ?
Answer: {
           "news":[
              {
                 "ID":1,
                 "name_news":"None_name",
                 "text_news":"None_text",
                 "img_path":"./img.png"
              },
              {
                 "ID":2,
                 "name_news":"None_name",
                 "text_news":"None_text",
                 "img_path":"./img.png"
              }
           ]
        }

This part on the site can also 
be seen by unauthorized users.
------------------------------------------------------




# /api/registration
------------------------------------------------------
Inquiry: ?name=testName&surname=testSurname&patronymic=testPatronymic&groups=pks-029&password=asdf&mail=mail222
Answer: {"token":"374bb40c-a445-4234-86a1-14f71e398869"}

The data is not particularly verified. 
This is bad
------------------------------------------------------



# /api/person_data
------------------------------------------------------
Inquiry: person_data?token=aaebccce-f501-4053-8d85-40f6dd158b46
Answer: {
           "ID":1,
           "Groups_ID":1,
           "token":"aaebccce-f501-4053-8d85-40f6dd158b46",
           "mail":"mail",
           "password":"pass",
           "surname":"Иванов",
           "name":"Иван",
           "patronymic":"Иванович",
           "grade_book_number":"pks-234"
        }


Additional data comes from the server, this is an error. 
It is also possible that there are some other data that 
would be worth storing in the database.
------------------------------------------------------




# /api/send_message
------------------------------------------------------
Inquiry: ?recipient=gmail&from_whom=mail&header=TestServer&text=test test test
Answer: {"result":true}


Request for a third party service:
HttpResponseProxy{HTTP/1.1 200 OK [Content-Type: application/json; charset=UTF-8, Date: Fri, 18 Jun 2021 11:16:55 GMT, Expires: Fri, 18 Jun 2021 11:16:55 GMT, Cache-Control: private, max-age=0, X-Content-Type-Options: nosniff, X-Frame-Options: SAMEORIGIN, Content-Security-Policy: frame-ancestors 'self', X-XSS-Protection: 1; mode=block, Server: GSE, Alt-Svc: h3=":443"; ma=2592000,h3-29=":443"; ma=2592000,h3-T051=":443"; ma=2592000,h3-Q050=":443"; ma=2592000,h3-Q046=":443"; ma=2592000,h3-Q043=":443"; ma=2592000,quic=":443"; ma=2592000; v="46,43", Transfer-Encoding: chunked] org.apache.http.client.entity.DecompressingEntity@1c24910d}
{"to":"dG8qfPtBD6r60NQw-6r-qa:APA91bGeR0IHj7QqxO3lr5uGtPdd4ix_0VQqGRMjz8tcGyLJWqZstjmyzWGZUPubhmsnzEwYJvZB-XjesbLyd6FlMhzIvqkbm5dJqgRv0BVGzo2FjL1e9IlU543PNcEx3zB_JagdFA2K","priority":"high","notification":{"title":"TestServer","body":"test test test"}}


This is where messages are sent to the client, the token is not needed for
this - and this is an error. Push notifications come through firebase, now this system is being finalized
------------------------------------------------------