package project.vilsoncake.Flowt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import project.vilsoncake.Flowt.entity.enumerated.ReportContentType;
import project.vilsoncake.Flowt.entity.enumerated.WhomReportType;

import java.util.Date;

@Entity
@Table(name = "report")
@Data
@NoArgsConstructor
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "whom_type")
    private WhomReportType whomType;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "content_type")
    private ReportContentType contentType;
    @Column(name = "content_name", nullable = false)
    private String contentTypeName;
    @CurrentTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
    @Column(name = "checked")
    private boolean checked = false;
    @Column(name = "checked_at", updatable = false)
    private Date checkedAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "whom_id")
    private UserEntity whom;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    public ReportEntity(WhomReportType whomType, ReportContentType contentType, String contentTypeName, UserEntity whom, UserEntity sender) {
        this.whomType = whomType;
        this.contentType = contentType;
        this.contentTypeName = contentTypeName;
        this.whom = whom;
        this.sender = sender;
    }
}
