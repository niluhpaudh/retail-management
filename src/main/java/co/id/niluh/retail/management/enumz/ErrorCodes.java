package co.id.niluh.retail.management.enumz;

import lombok.ToString;

@ToString
public enum ErrorCodes {
	BAD_CREDENTIAL("Username atau password salah"),
	UNEXPECTED("Kesalahan system"),
	CONSTRAINT_VIOLATION("Kesalahan pada database"),
	PARSE_ERROR("Kesalahan parsing data"),
	SQL_ERROR("Kesalahan pada sql database"),
	REQUEST_FIELD("Data request dibutuhkan"),
	FORBIDDEN_ACCESS("Akses tidak diizinkan"),
    USER_NOT_FOUND("User tidak ditemukan"),
    PRODUCT_NOT_FOUND("Produk tidak ditemukan"),
    OUT_OF_STOCK("Stok Produk Kosong");

    private String defaultMessage;
    private String messageCode;

    ErrorCodes(String defaultMessage) {
		this.defaultMessage = defaultMessage;
		this.messageCode = "application.error.message.";
    }


    public String getDefaultMessage() {
        return defaultMessage;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
