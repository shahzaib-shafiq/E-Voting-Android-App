package com.example.smd_final_project;




public class Imagecode {
//    database.getReference().child("image").addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            String img = snapshot.getValue(String.class);
////                Picasso.get().load(img).into();
////                Uri imgUri = Uri.parse(img);
//            int currentPosition = viewPagerAdmin.getCurrentItem();
//            Fragment currentFragment = fragmentArrayList.get(currentPosition);
//            if (currentFragment instanceof AdminHomeFragment) {
//                AdminHomeFragment adminHomeFragment = (AdminHomeFragment) currentFragment;
//                adminHomeFragment.setImageByPicasso(img);
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });
//    launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//        @Override
//        public void onActivityResult(Uri result) {
//            int currentPosition = viewPagerAdmin.getCurrentItem();
//            Fragment currentFragment = fragmentArrayList.get(currentPosition);
//            if (currentFragment instanceof AdminHomeFragment) {
//                AdminHomeFragment adminHomeFragment = (AdminHomeFragment) currentFragment;
//                adminHomeFragment.setImage(result);
//                StorageReference storageReference = storage.getReference().child("image");
//                storageReference.putFile(adminHomeFragment.getImage()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                database.getReference().child("image").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(AdminMainActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//            }
//        }
//    });

//    public void SetImage(View view) {
//        launcher.launch("image/*");
//    }
}
