import firebase_admin
from firebase_admin import credentials, firestore, messaging

cred = credentials.Certificate("serviceAccountKey.json")
firebase_admin.initialize_app(cred)

db = firestore.client()

def send_notification(token, title, body, request_id, status):
    message = messaging.Message(
        notification = messaging.Notification(
            title = title,
            body = body,
        ),
        data = {
            "reqId" : request_id,
            "status" : status
        },
        token = token
    )
    
    response = messaging.send(message)
    print("Notification sent:", response)

def req_handler(col_snapshot, changes, read_time):
    for change in changes:
        print("📡 Firestore triggered a change")
        doc = change.document.to_dict()
        request_id = change.document.id
        new_status = doc.get("status")

        print(f"Status of request {request_id} is changed to {new_status}.")

        user_id = doc.get("rqUser")
        user_ref = db.collection("user").document(user_id).get()
        if user_ref.exists:
            fcm_token = user_ref.to_dict().get("fcmToken")
            if fcm_token:
                if new_status is True:
                    send_notification(fcm_token, "Request Confirmed", f"Your Request {request_id} has been confirmed.", request_id, new_status)
                elif new_status is False:
                    send_notification(fcm_token, "Request Rejected", f"Your Request {request_id} has been rejected.", request_id, new_status)
            else:
                print(f"FCM Token not found for {user_id}")


col_query = db.collection("requests")
col_query.on_snapshot(req_handler)

from flask import Flask

app = Flask(__name__)

@app.route("/")
def home():
    return "Backend listener is running!"

if __name__ == "__main__":
    app.run(debug=True, use_reloader=False)  # use_reloader=False prevents double listeners