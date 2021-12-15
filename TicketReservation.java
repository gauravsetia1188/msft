 It's only missing to handle the cases when a user just call the getReservationNumber and never confirm it.
In this case, we should mention that we could: Run a diff thread for this, which pulls things out expired items from prioriy queue




public class TicketService {

private int capacity;
private int occupiedCounts;
private int pendingCounts;
private Queue<Ticket> available;
private Map<String, Ticket> pendingMap;
private Set<Ticket> confirmed;
private final static long TIMEOUT = Duration.ofSeconds(30).getSeconds() / 1000;; 

public TicketService(int capacity) {
    this.capacity = capacity;
    this.occupiedCounts = 0;
    this.pendingCounts = 0;
    this.available = new LinkedList<>();
    this.confirmed = new HashSet<>();
    this.pendingMap = new HashMap<>();

    for(int i=0; i<this.capacity; ++i) {
        available.add(new Ticket(String.valueOf(i), -1));
    }
}

/**
 * Return a String of reservation number for a given ticket.
 * 
 * @return the reservation number
 * @throws Exception
 */
public String getReservationNumber() throws Exception {
    if(this.occupiedCounts + this.pendingCounts >= this.capacity) {
        throw new Exception("No available tickets");
    }
    Ticket nextTicket = this.available.poll();
    nextTicket.setCreatedBy(System.currentTimeMillis());
    this.pendingMap.put(nextTicket.getId(), nextTicket);
    this.pendingCounts++;
    return nextTicket.getId();
}

/**
 * Return true or false depending on whether the ticket was confirmed with N
 * (say 30) seconds
 * 
 * @param reservationNumber
 * @return
 * @throws Exception
 */
public boolean confirmReservation(String reservationNumber) throws Exception {
    Ticket ticket = this.pendingMap.get(reservationNumber);
    if(ticket == null) {
        throw new Exception("Ticket is not pending");
    }

    pendingMap.remove(ticket.getId());
    this.pendingCounts--;

    if(ticket.getConfirmation() && ticket.getConfirmedBy() - ticket.getCreatedBy() < TIMEOUT) {
        this.occupiedCounts++;
        confirmed.add(ticket);
        return true;
    }
    
    ticket.free();
    available.offer(ticket);
    return false;
}
}

public class Ticket implements Comparable {
private String id;
private long createdBy;
private long confirmedBy;
private TicketState state;

public Ticket(String id, long createdBy) {
    this.id = id;
    this.createdBy = createdBy;
    this.state = TicketState.AVAILABLE;
}

public void setId(String id) {
    this.id = id;
}

public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
}

public void confirm() {
    this.state = TicketState.CONFIRMED;
}

public void onHold() {
    this.state = TicketState.PENDING;
}

public void free() {
    this.createdBy = -1;
    this.state = TicketState.AVAILABLE;
}

public String getId() {
    return this.id;
}

public long getCreatedBy() {
    return this.createdBy;
}

public boolean getConfirmation() {
    return this.state == TicketState.CONFIRMED;
}

public boolean isAvailable() {
    return this.state == TicketState.AVAILABLE;
}

public long getConfirmedBy() {
    return confirmedBy;
}

public void setConfirmedBy(long confirmedBy) {
    this.confirmedBy = confirmedBy;
}

@Override
public int compareTo(Ticket o) {
    return Long.compare(this.createdBy, o.createdBy);
}
}

public enum TicketState {
AVAILABLE,
PENDING,
CONFIRMED
}
