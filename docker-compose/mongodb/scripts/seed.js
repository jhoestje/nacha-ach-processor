// seed.js
db = db.getSiblingDB("nachadb");
db.paymentBatchStates.insertMany([
  { state: "PENDING" },
  { state: "PROCESSING" },
  { state: "COMPLETE" },
  { state: "CANCELED" },
  { state: "FAILED" }
]);