package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.Format;
import com.bookmyshow.bmscore.enums.Language;
import com.bookmyshow.bmscore.enums.ShowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="shows")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Show extends GlobalFields{
    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;

    @Enumerated(EnumType.STRING)
    private Language language;
    @Enumerated(EnumType.STRING)
    private Format format;
    @Enumerated(EnumType.STRING)
    private ShowStatus showStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private boolean isActive = true;

//    public void setSysId(String sysId) {
//        super.sysId = sysId;
//    }
}