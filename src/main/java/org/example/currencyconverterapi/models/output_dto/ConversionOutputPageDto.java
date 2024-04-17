package org.example.currencyconverterapi.models.output_dto;

import java.util.List;

public class ConversionOutputPageDto {
    private int currentPage;
    private int totalPages;
    private List<ConversionOutputDto> conversionList;

    public ConversionOutputPageDto() {

    }

    public ConversionOutputPageDto(int currentPage,
                                   int totalPages,
                                   List<ConversionOutputDto> conversionList) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.conversionList = conversionList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ConversionOutputDto> getConversionList() {
        return conversionList;
    }

    public void setConversionList(List<ConversionOutputDto> conversionList) {
        this.conversionList = conversionList;
    }
}
