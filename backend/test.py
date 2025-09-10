import firebase_admin
from firebase_admin import credentials, firestore, messaging

cred = credentials.Certificate("serviceAccountKey.json")
firebase_admin.initialize_app(cred)

db = firestore.client()
collection_ref = db.collection("requests")

# Step 1: Take baseline snapshot on startup
baseline = {}
for doc in collection_ref.stream():
    baseline[doc.id] = doc.to_dict()

print("Baseline snapshot taken:", baseline.keys())


def send_notification(token, title, body, request_id, status):
    message = messaging.Message(
        notification=messaging.Notification(
            title=title,
            body=body,
        ),
        data={
            "request_id": request_id,
            "status": status
        },
        token=token
    )
    response = messaging.send(message)
    print("Notification sent:", response)


# Step 2: Listen for changes
def on_snapshot(col_snapshot, changes, read_time):
    global baseline
    for change in changes:
        doc_id = change.document.id
        doc_data = change.document.to_dict()

        if change.type.name == "ADDED":
            if doc_id not in baseline:
                print(f"New doc added: {doc_id}")
                # send_notification(...)
        elif change.type.name == "MODIFIED":
            old_data = baseline.get(doc_id)
            if old_data != doc_data:  # only if data actually changed
                print(f"Doc modified: {doc_id}")
                # send_notification(...)
        
        # Update baseline so we don’t re-trigger on restart
        baseline[doc_id] = doc_data


col_watch = collection_ref.on_snapshot(on_snapshot)
