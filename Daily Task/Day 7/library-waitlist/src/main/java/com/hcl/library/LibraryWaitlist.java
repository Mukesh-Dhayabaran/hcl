
package com.hcl.library;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class LibraryWaitlist {

    // ArrayList: maintains the registered member catalogue.
    private final List<String> members = new ArrayList<>();

    // LinkedHashSet: prevents duplicates and preserves insertion order.
    private final Set<String> uniqueMembers = new LinkedHashSet<>();

    // ArrayDeque: maintains the normal waitlist in FIFO order.
    private final Deque<String> waitlist = new ArrayDeque<>();

    // PriorityQueue: serves lower-numbered priorities first.
    private final PriorityQueue<PriorityRequest> priorityQueue =
            new PriorityQueue<>();

    // ArrayList: stores the library's book catalogue.
    private final List<String> catalogue = new ArrayList<>(
            List.of("Java Basics", "Effective Java", "Clean Code",
                    "Spring in Action"));

    public void registerMember(String name) {
        if (uniqueMembers.add(name)) {
            members.add(name);
            waitlist.offerLast(name);
            System.out.println("Registered: " + name);
        } else {
            System.out.println("Already registered: " + name);
        }
    }

    public void addPriorityMember(String name, int priority) {
        if (!uniqueMembers.contains(name)) {
            System.out.println("Register the member first: " + name);
            return;
        }

        if (priority < 1) {
            System.out.println("Priority must be at least 1.");
            return;
        }

        priorityQueue.offer(new PriorityRequest(name, priority));
        System.out.println("Priority added: " + name
                + " (priority " + priority + ")");
    }

    public void serveNextMember() {
        String member = waitlist.pollFirst();

        if (member == null) {
            System.out.println("The waitlist is empty.");
        } else {
            System.out.println("Serving next member: " + member);
        }
    }

    public void serveNextPriorityMember() {
        PriorityRequest request = priorityQueue.poll();

        if (request == null) {
            System.out.println("No priority members waiting.");
        } else {
            System.out.println("Serving priority member: "
                    + request.memberName());
        }
    }

    public void removeMember(String name) {
        boolean removed = false;

        // Iterator.remove safely removes an element during iteration.
        Iterator<String> iterator = members.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().equals(name)) {
                iterator.remove();
                removed = true;
                break;
            }
        }

        if (removed) {
            uniqueMembers.remove(name);
            waitlist.removeFirstOccurrence(name);
            priorityQueue.removeIf(
                    request -> request.memberName().equals(name));
            System.out.println("Removed member: " + name);
        } else {
            System.out.println("Member not found: " + name);
        }
    }

    public void displayMembers() {
        System.out.println("\nRegistered members: " + members);
        System.out.println("Unique members: " + uniqueMembers);
        System.out.println("Normal waitlist: " + waitlist);
        System.out.println("Priority queue: " + priorityQueue);
    }

    public static void main(String[] args) {
        LibraryWaitlist library = new LibraryWaitlist();

        library.registerMember("Arun");
        library.registerMember("Priya");
        library.registerMember("Kumar");
        library.registerMember("Arun"); // Test duplicate registration.

        // Priority 1 = faculty; priority 2 = regular priority request.
        library.addPriorityMember("Kumar", 2);
        library.addPriorityMember("Priya", 1);

        library.displayMembers();

        library.serveNextMember();
        library.serveNextPriorityMember();

        library.removeMember("Arun");
        library.displayMembers();

        library.displayCatalogue();

        Set<String> overdueItems = Set.of("Java Basics", "Clean Code");
        library.removeOverdueItems(overdueItems);

        library.displayCatalogue();
    }

    public void removeOverdueItems(Set<String> overdueItems) {
        Iterator<String> iterator = catalogue.iterator();

        while (iterator.hasNext()) {
            String item = iterator.next();

            if (overdueItems.contains(item)) {
                iterator.remove();
                System.out.println("Removed overdue item: " + item);
            }
        }
    }

    public void displayCatalogue() {
        System.out.println("Library catalogue: " + catalogue);
    }
}
