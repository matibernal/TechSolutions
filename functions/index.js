/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

const functions = require("firebase-functions");
const admin = require("firebase-admin");
const sgMail = require("@sendgrid/mail");

admin.initializeApp();

const SENDGRID_API_KEY = functions.config().sendgrid.key;
sgMail.setApiKey(SENDGRID_API_KEY);

exports.sendContactEmail = functions.https.onCall(async (data, context) => {
  // Datos enviados desde la app
  const { nombre, apellido, email, mensaje } = data;

  // Correo al que le va a llegar (el admin)
  const toAdmin = "matrekb@gmail.com"; // email del admin

  const msg = {
    to: toAdmin,
    from: {
      email: "matrekb@gmail.com", // El email verificado de SendGrid osea el sender
      name: "TechSolutions Soporte"
    },
    subject: "Nueva consulta de contacto en TechSolutions",
    html: `
      <strong>Nombre:</strong> ${nombre}<br>
      <strong>Apellido:</strong> ${apellido}<br>
      <strong>Email:</strong> ${email}<br>
      <strong>Mensaje:</strong><br>${mensaje}
    `,
  };

  try {
    await sgMail.send(msg);
    return { success: true, message: "Mensaje enviado correctamente" };
  } catch (error) {
    console.error(error);
    return { success: false, message: "Error al enviar el mensaje" };
  }
});



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
