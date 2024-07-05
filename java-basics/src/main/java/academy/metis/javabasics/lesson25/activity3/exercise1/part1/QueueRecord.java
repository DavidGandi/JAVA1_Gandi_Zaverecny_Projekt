package academy.metis.javabasics.lesson25.activity3.exercise1.part1;

import java.time.LocalDate;

public class QueueRecord {
    private final long queueItemId;
    private final long memberId;
    private final long titleId;
    private final LocalDate addedToQueueDate;
    private boolean isResolved;

    public QueueRecord(long queueItemId, long memberId, long titleId, LocalDate addedToQueueDate, boolean isResolved) {
        this.queueItemId = queueItemId;
        this.memberId = memberId;
        this.titleId = titleId;
        this.addedToQueueDate = addedToQueueDate;
        this.isResolved = isResolved;
    }
    public long getQueueItemId() {
        return queueItemId;
    }
    public long getMemberId() {
        return memberId;
    }
    public long getTitleId() {
        return titleId;
    }

    public LocalDate getAddedToQueueDate() {
        return addedToQueueDate;
    }
    public boolean isResolved() {
        return isResolved;
    }
    public void setResolved(boolean resolved) {
        this.isResolved = resolved;
    }
    @Override
    public String toString() {
        return queueItemId +
                ". | Member Id = " + memberId +
                "| Title Id = " + titleId +
                "| Added to Queue = " + addedToQueueDate +
                "| Resolved = " +  ((isResolved) ? "RESOLVED" : "NOT RESOLVED");
    }
}