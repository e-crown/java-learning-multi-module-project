/**
 * 
 */
package com.kv.wc.common.enums;

/**
 * 文件类型，魔数ENUM
 */
public enum FileType {
    /**
     * JEPG/JPG.
     */
    JPEG("JPG", "FFD8FF"),

    /**
     * PNG.
     */
    PNG("PNG", "89504E47"),

    /**
     * svg.
     */
    SVG("SVG", "3C737667"),

    /**
     * GIF.
     */
    GIF("GIF", "47494638"),

    /**
     * Windows Bitmap.
     */
    BMP("BMP", "424D"),

    /**
     * TIFF.
     */
    TIFF("TIFF", "49492A00"),

    /**
     * CAD.
     */
    DWG("DWG", "41433130"),

    /**
     * Adobe Photoshop.
     */
    PSD("PSD", "38425053"),

    /**
     * XML.
     */
    XML("XML", "3C3F786D6C"),

    /**
     * HTML.
     */
    HTML("HTML", "68746D6C3E"),

    /**
     * Adobe Acrobat.
     */
    PDF("PDF", "255044462D312E"),

    /**
     * RAR Archive.
     */
    RAR("RAR", "52617221"),

    /**
     * Wave.
     */
    WAV("WAV", "57415645"),

    /**
     * wmv
     */
    WMV("WMV", "3026B2758E66CF"),

    /**
     * AVI.
     */
    AVI("AVI", "52494646428B02"),

    /**
     * MOV
     */
    MOV("MOV", "00000014667479"),

    /**
     * MP4
     */
    MP4("MP4", "0000001C667479706D"),

    /**
     * Real Media (rm) rmvb/rm相同
     */
    RMVB("RMVB", "2E524D46"),

    /**
     * FLV
     */
    FLV("FLV", "464C5601050000"),

    /**
     * 3GP
     */
    GP("3GP", "0000001C6674797033"),

    /**
     * MPG
     */
    MPG("MPG", "000001BA44000400"),

    /**
     * APK
     */
    APK("APK", "504B0304"),;

    private String type = "";

    private String value = "";

    /**
     * Constructor.
     * 
     * @param value
     */
    private FileType(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
