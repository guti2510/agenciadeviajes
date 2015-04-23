package com.luispc.travelangency;


import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * Created by LuisPC on 4/22/15.
 */
public class Mail {

    private final String mail = "<div style=\"background-color: #FFFFC9; width: 100%; padding: 30px 0px;\"><table style=\"width: 513px;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><tbody><tr align=\"left\"><td style=\"width: 513px;\">&nbsp;</td></tr><tr bgcolor=\"#303024\"><td style=\"font-size: 21px; font-family: ''Trebuchet MS''; color: #ffffff; width: 513px; padding-left: 15px;\" height=\"45\">Reservacion Agencia de Viajes Tu Ruta</td></tr><tr style=\"background-color: #ffffff;\"><td><table style=\"width: 513px;\" border=\"0\" cellspacing=\"18\" cellpadding=\"0\"><tbody><tr><td style=\"font-size: 13px; font-family: ''Trebuchet MS''; color: #333333;\"><p>Nombre: $Nombre</p><p>Cantidad de Personas: $Cantidad</p><p>Dia Reservado: $DiaReservado</p><p>Fecha Final: $FechaFinal</p>\n" +
            "<p>Precio Final: $Precio</p><p>Tipo de Reservacion: $Tipo</p></td></tr></tbody></table></td></tr></tbody></table></div>";

    public String _SystemMail = "travelagencyserver@gmail.com";
    private String _SystemMailPass = "travelagencyserver2015";


    // Replaces the values on the email Body
    private String getMailBody (InfoReservacion pReservacion){

        String emailBody = mail;

        emailBody = emailBody.replace("$Nombre",pReservacion.getNombre());
        emailBody = emailBody.replace("$Cantidad",pReservacion.getCantidadPersonas());
        emailBody = emailBody.replace("$DiaReservado",pReservacion.getDiaReservado());
        emailBody = emailBody.replace("$FechaFinal",pReservacion.getFechaFinal());
        emailBody = emailBody.replace("$Precio",pReservacion.getPrecioFinal());
        emailBody = emailBody.replace("$Tipo",pReservacion.getTipoReserva());

        return emailBody;
    }

    public void sendMail(String email, String subject,InfoReservacion pReservacion) {

        String messageBody = getMailBody(pReservacion);
        Session session = createSessionObject();

        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Message createMessage(String email, String subject, String messageBody, Session session)
            throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(_SystemMail, "Agencia de viajes Tu Ruta"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        //message.setText(messageBody);
        message.setContent(messageBody, "text/html; charset=utf-8");
        return message;
    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(_SystemMail, _SystemMailPass);
            }
        });
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(Mail.this, "Espere", "Enviando correo", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }




}
