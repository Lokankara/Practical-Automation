package com.softserve.edu.teachua.data;

public enum WebinarChallenges {
    VUYTIK("Як почати навчати? Перші кроки до української мови викладання.", "Вуйтік", "https://www.youtube.com/watch?v=JMAF_pSOBws"),
    LELYK("Запам'ятовувати легко: українська мова + ейдетика та мнемотехніка.", "Лелик", "https://www.youtube.com/watch?v=o_2rAf5M1w0"),
    BORIS("\"До Олімпу через українське слово\". Вебінар про українську спортивну термінологію", "Борис", "https://www.youtube.com/watch?v=5-w8dD-JXgk"),
    ZHUGAN("Голос і мовлення. Олександр Жуган", "Жуган", "https://www.youtube.com/watch?v=-QRBoDNdRp8");

    private final String title;
    private final String header;
    private final String url;

    WebinarChallenges(String title, String header, String url) {
        this.title = title;
        this.header = header;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getHeader() {
        return header;
    }

    public String getUrl() {
        return url;
    }
}
