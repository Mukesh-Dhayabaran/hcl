package com.hcl.library;

public record PriorityRequest(String memberName, int priority)
        implements Comparable<PriorityRequest> {

    @Override
    public int compareTo(PriorityRequest other) {
        int result = Integer.compare(this.priority, other.priority);

        // For equal priorities, preserve alphabetical ordering.
        if (result == 0) {
            return this.memberName.compareTo(other.memberName);
        }

        return result;
    }
}
