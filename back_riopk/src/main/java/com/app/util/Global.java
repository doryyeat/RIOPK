package com.app.util;

import com.app.ordering.Ordering;
import com.app.system.exception.BadRequestException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

public class Global {

    public static final String uploadImg = getUploadImg();

    public static final String ADMIN = "ADMIN";
    public static final String MANAGER = "MANAGER";
    public static final String USER = "USER";

    static String EMAIL_USERNAME = "";
    static String EMAIL_PASSWORD = "";

    private static String getUploadImg() {
        StringBuilder dir = new StringBuilder(System.getProperty("user.dir"));
        for (int j = 0; j < dir.length(); j++) {
            if (dir.charAt(j) == '\\') {
                dir.setCharAt(j, '/');
            }
        }

        if (dir.toString().startsWith("/application")) {
            dir.append("/BOOT-INF/classes/img");
        } else {
            dir.append("/src/main/resources/img");
        }

        return dir.toString();
    }

    public static String getDateFormatted(String date) {
        return LocalDate.parse(date).format(DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru")));
    }

    public static String getDateTimeFormatted(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).format(DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm:ss", new Locale("ru")));
    }

    private static String getDatetime() {
        return LocalDateTime.now().toString();
    }

    public static String getDateNow() {
        return getDatetime().substring(0, 10);
    }

    public static String getTimeNow() {
        return getDatetime().substring(11, 19);
    }

    public static String getDateAndTimeNow() {
        return getDateNow() + " " + getTimeNow();
    }

    public static String saveFile(MultipartFile file, String path) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            File uploadDir = new File(uploadImg);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String result = path + "/" + uuidFile + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadImg + "/" + result));
            return "http://localhost:8080/img/" + result;
        } else throw new IOException();
    }

    public static float round(float value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (float) tmp / factor;
    }

    public static void email_message(String email, Ordering ordering) {
        final String username = EMAIL_USERNAME;
        final String password = EMAIL_PASSWORD;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Вас приветствует Attest! Готовы дарить эмоции?");

            String text = "Здравствуйте!" +
                    "\n" +
                    "\nУ нас для вас прекрасные новости — у вас есть подарочный сертификат от компании Attest" +
                    "\n" +
                    "\nКто-то очень о вас позаботился и хочет сделать ваш день ярче." +
                    "\n" +
                    "\nДетали вашего сертификата:" +
                    "\n" +
                    "\nПодарочный сертификат на " + ordering.getCert().getName() +
                    "\nКод сертификата для активации " + ordering.getCode() +
                    "\nСрок действия по " + ordering.getDateEndFormat() +
                    "\nАдрес " + ordering.getCert().getAddress() +
                    "\n" +
                    "\nКак им воспользоваться?" +
                    "\n" +
                    "\n1. Приезжайте по указанному адресу" +
                    "\n2. Сообщите сотруднику организации ваш уникальный код" +
                    "\n3. Поздравляю! Отдыхайте" +
                    "\n" +
                    "\nЕсли у вас остались вопросы, просто вы можете позвонить нам по номеру +37529777555999" +
                    "\n" +
                    "\nПоздравляем вас с этим приятным сюрпризом!" +
                    "\n" +
                    "\nС наилучшими пожеланиями," +
                    "\nКоманда Attest";

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(text, "utf-8");
            multipart.addBodyPart(textPart);


            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                File fontFile = new File("src/main/resources/fonts/email_font.ttf");
                PDType0Font font;
                if (fontFile.exists()) {
                    font = PDType0Font.load(document, fontFile);
                } else {
                    throw new BadRequestException("Ошибка! не найден шрифт");
                }

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.beginText();
                    contentStream.setFont(font, 12);
                    contentStream.newLineAtOffset(50, 700);

                    String[] lines = text.split("\n");
                    float leading = 14.5f;

                    for (String line : lines) {
                        contentStream.showText(line);
                        contentStream.newLineAtOffset(0, -leading);
                    }

                    contentStream.endText();
                }

                document.save(baos);
            } catch (IOException e) {
                throw new BadRequestException("Ошибка при создании PDF файла");
            }

            MimeBodyPart pdfAttachment = new MimeBodyPart();
            ByteArrayDataSource dataSource = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
            pdfAttachment.setDataHandler(new DataHandler(dataSource));
            pdfAttachment.setFileName("Подарочный_сертификат_Attest.pdf");
            multipart.addBodyPart(pdfAttachment);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
