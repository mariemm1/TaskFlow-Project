export interface EquipeAssignmentRequest {
  chefId: number | null;         // Can be null if no chef assigned
  membreIds: number[];           // List of member IDs
}
