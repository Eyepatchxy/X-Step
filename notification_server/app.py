import os
from flask import Flask, request, jsonify
import firebase_admin
from firebase_admin import credentials, messaging
from dotenv import load_dotenv

load_dotenv()

# Initialize Firebase Admin SDK
cred = credentials.Certificate("serviceAccountKey.json")
firebase_admin.initialize_app(cred)

app = Flask(__name__)

@app.route("/send", methods=["POST"])
def send_notification():
    data = request.get_json()
    title = data.get("title")
    body = data.get("body")
    token = data.get("token")  # Or use topic instead

    # Construct message
    message = messaging.Message(
        notification=messaging.Notification(
            title=title,
            body=body
        ),
        token=token,  # or use topic='some-topic'
    )

    # Send message
    response = messaging.send(message)
    return jsonify({"message_id": response}), 200


if __name__ == "__main__":
    app.run(debug=True)



from flask import request, jsonify
from firebase_admin import messaging

@app.route('/send-notification', methods=['POST'])
def send_notification():
    try:
        data = request.get_json()
        title = data.get('title')
        body = data.get('body')
        token = data.get('token')  # can also be "topic"

        if not title or not body or not token:
            return jsonify({'error': 'Missing fields'}), 400

        message = messaging.Message(
            notification=messaging.Notification(
                title=title,
                body=body
            ),
            token=token  # use `.topic` instead of `.token` if you want to send to a topic
        )

        response = messaging.send(message)
        return jsonify({'success': True, 'response': response})

    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 500
