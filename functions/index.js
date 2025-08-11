/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const {setGlobalOptions} = require("firebase-functions");
const {onRequest} = require("firebase-functions/https");
const logger = require("firebase-functions/logger");

// For cost control, you can set the maximum number of containers that can be
// running at the same time. This helps mitigate the impact of unexpected
// traffic spikes by instead downgrading performance. This limit is a
// per-function limit. You can override the limit for each function using the
// `maxInstances` option in the function's options, e.g.
// `onRequest({ maxInstances: 5 }, (req, res) => { ... })`.
// NOTE: setGlobalOptions does not apply to functions using the v1 API. V1
// functions should each use functions.runWith({ maxInstances: 10 }) instead.
// In the v1 API, each function can only serve one request per container, so
// this will be the maximum concurrent request count.
setGlobalOptions({ maxInstances: 10 });

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });



const functions = require("firebase-functions");
const admin = require("firebase-admin");

admin.initializeApp();

exports.notifyRequestStatusChange = functions.firestore
  .document("requests/{reqId}")
  .onUpdate(async (change, context) => {
    const beforeData = change.before.data();
    const afterData = change.after.data();

    const prevStatus = beforeData.status;
    const newStatus = afterData.status;

    // Only act if status was previously null and changed to true or false
    if (prevStatus === null && newStatus !== null) {
      let title = "";
      let message = "";

      if (newStatus === true) {
        title = "Request Confirmed";
        message = `Your request for ${afterData.item} has been accepted!`;
      } else if (newStatus === false) {
        title = "Request Rejected";
        message = `Sorry, your request for ${afterData.item} was rejected.`;
      } else {
        return null;
      }

      const rqUser = afterData.rqUser;

      // Fetch the user's FCM token
      const userDoc = await admin.firestore().collection("user").doc(rqUser).get();
      const userToken = userDoc.data()?.fcmToken;

      if (!userToken) {
        console.log(`No FCM token found for user: ${rqUser}`);
        return null;
      }

      const payload = {
        notification: {
          title: title,
          body: message,
        },
        token: userToken,
      };

      // Send the notification
      try {
        const response = await admin.messaging().send(payload);
        console.log("Notification sent successfully:", response);
      } catch (error) {
        console.error("Error sending notification:", error);
      }
    }

    return null;
  });
